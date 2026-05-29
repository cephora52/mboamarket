import { Component, OnInit, OnDestroy, ViewChild, ElementRef } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { ActivatedRoute, Router, RouterModule } from '@angular/router';
import { MessageService, MessageDTO, ConversationDTO } from '../services/message.service';
import { NavbarComponent } from '../navbar/navbar.component';
import { AuthService } from '../services/auth.service';
import { ModalService } from '../services/modal.service';
import { interval, Subscription } from 'rxjs';

@Component({
  selector: 'app-chat',
  standalone: true,
  imports: [CommonModule, FormsModule, RouterModule, NavbarComponent],
  templateUrl: './chat.component.html',
  styleUrl: './chat.component.css'
})
export class ChatComponent implements OnInit, OnDestroy {
  @ViewChild('scrollMe') private myScrollContainer!: ElementRef;
  @ViewChild('fileInput') fileInput!: ElementRef;

  currentUser: any = null;
  conversations: ConversationDTO[] = [];
  messages: MessageDTO[] = [];
  selectedConversation: ConversationDTO | null = null;
  newMessage: string = '';
  searchConvTerm: string = '';
  pollingSub: Subscription | null = null;
  lastError: any = null;
  showLogoutModal = false;

  constructor(
    private messageService: MessageService,
    public authService: AuthService,
    private route: ActivatedRoute,
    public router: Router,
    private modal: ModalService
  ) {}

  get filteredConversations(): ConversationDTO[] {
    if (!this.searchConvTerm.trim()) return this.conversations;
    const term = this.searchConvTerm.toLowerCase();
    return this.conversations.filter(c => 
      c.otherUserName.toLowerCase().includes(term) || 
      (c.lastMessage && c.lastMessage.toLowerCase().includes(term))
    );
  }

  ngOnInit(): void {
    this.currentUser = this.authService.getUser();

    if (!this.currentUser) {
      this.router.navigate(['/']);
      return;
    }
    this.loadConversations();

    this.route.queryParams.subscribe(params => {
      const targetUserId = params['u'];
      if (targetUserId) {
        this.openConversationWith(parseInt(targetUserId));
      }
    });

    this.pollingSub = interval(3000).subscribe(() => {
      if (this.selectedConversation) {
        this.loadMessages();
      }
      this.loadConversations();
    });
  }

  ngOnDestroy(): void {
    if (this.pollingSub) {
      this.pollingSub.unsubscribe();
    }
  }

  logout() {
    this.authService.logout();
    this.router.navigate(['/']);
  }

  loadConversations(): void {
    const userId = this.authService.getUserId();
    if (userId) {
      this.messageService.getConversations(userId).subscribe({
        next: (data) => {
          this.conversations = data;
          this.lastError = null;
        },
        error: (err) => {
          this.lastError = err;
  
        }
      });
    }
  }

  testBackend(): void {
    const userId = this.authService.getUserId();
    if (!userId) {
      this.modal.show({ type: 'error', title: 'Non connecté', message: 'Test impossible: vous n\'êtes pas connecté' });
      return;
    }
    this.messageService.getConversations(userId).subscribe({
      next: (res) => this.modal.show({ type: 'success', title: 'Connexion réussie', message: res.length + ' conversations trouvées.' }),
      error: (err) => {
        this.lastError = err;
        this.modal.show({ type: 'error', title: 'Erreur serveur', message: 'Échec de connexion au serveur !' });
      }
    });
  }

  selectConversation(conv: ConversationDTO): void {
    this.selectedConversation = conv;
    this.loadMessages();
    this.markSelectedAsRead();
  }

  markSelectedAsRead(): void {
    const currentId = this.authService.getUserId();
    if (this.selectedConversation && currentId) {
      this.messageService.markAsRead(this.selectedConversation.idConversation, currentId).subscribe();
    }
  }

  loadMessages(): void {
    if (!this.selectedConversation) return;
    const currentId = Number(this.authService.getUserId());
    if (!currentId) return;

    const otherUserId = Number(this.selectedConversation.idUser1) === currentId 
      ? Number(this.selectedConversation.idUser2) 
      : Number(this.selectedConversation.idUser1);
    
    this.messageService.getMessagesBetween(currentId, otherUserId).subscribe({
      next: (data) => {
        this.messages = data;
        if (this.messages.length > 0) {
          setTimeout(() => this.scrollToBottom(), 100);
        }
      },
      error: (err) => {
        console.error("Erreur récupération messages:", err);
      }
    });
  }

  openConversationWith(otherUserId: number): void {
    const currentId = this.authService.getUserId();
    if (!currentId) {
      return;
    }

    // On pré-sélectionne immédiatement pour afficher l'interface d'envoi
    this.selectedConversation = {
      idConversation: 0,
      dateCreation: new Date(),
      idUser1: currentId,
      idUser2: otherUserId,
      otherUserName: 'Chargement...',
      otherUserPhoto: ''
    } as ConversationDTO;

    this.authService.getUserById(otherUserId).subscribe(user => {
      if (this.selectedConversation) {
        this.selectedConversation.otherUserName = user.nom;
        this.selectedConversation.otherUserPhoto = user.photo;
      }
    });

    this.messageService.getMessagesBetween(currentId, otherUserId).subscribe(data => {
      this.messages = data;
      const existing = this.conversations.find(c => Number(c.idUser1) === Number(otherUserId) || Number(c.idUser2) === Number(otherUserId));
      if (existing) {
        this.selectedConversation = existing;
        this.markSelectedAsRead();
      }
      setTimeout(() => this.scrollToBottom(), 200);
    });
  }

  sendMessage(): void {
    if (!this.newMessage.trim() || !this.selectedConversation) {
      return;
    }
    this.sendInternal(this.newMessage.trim(), null);
    this.newMessage = '';
  }

  public sendInternal(content: string | null, image: string | null): void {
    const currentId = this.authService.getUserId();
    if (!currentId || !this.selectedConversation) {
      return;
    }

    const otherUserId = Number(this.selectedConversation.idUser1) === Number(currentId) 
      ? Number(this.selectedConversation.idUser2) 
      : Number(this.selectedConversation.idUser1);

    if (!currentId || !otherUserId || isNaN(currentId) || isNaN(otherUserId)) {
        this.modal.show({ type: 'error', title: 'Erreur', message: 'ERREUR CRITIQUE : ID Expéditeur ou Destinataire invalide !' });
        return;
    }

    const message: any = {
      contenu: content || '',
      image: image || null,
      idExpediteur: Number(currentId),
      idDestinataire: Number(otherUserId)
    };

    if (this.selectedConversation && this.selectedConversation.idConversation > 0) {
      message.idConversation = this.selectedConversation.idConversation;
    }

    this.messageService.sendMessage(message).subscribe({
      next: (res) => {
        if (this.selectedConversation && (this.selectedConversation.idConversation === 0 || !this.selectedConversation.idConversation)) {
          this.selectedConversation.idConversation = res.idConversation!;
        }

        this.messages.push(res);
        this.newMessage = ''; 
        
        this.loadConversations();
        setTimeout(() => this.scrollToBottom(), 100);
      },
      error: (err) => {
        const detail = err.error?.message || err.message || "Impossible de joindre le serveur";
        this.modal.show({ type: 'error', title: 'Erreur serveur', message: detail });
      }
    });
  }

  triggerUpload(): void {
    this.fileInput.nativeElement.click();
  }

  onFileSelected(event: any): void {
    const file = event.target.files[0];
    if (file) {
      const reader = new FileReader();
      reader.onload = (e: any) => {
        const base64 = e.target.result;
        this.sendInternal(null, base64);
      };
      reader.readAsDataURL(file);
    }
  }

  scrollToBottom(): void {
    try {
      this.myScrollContainer.nativeElement.scrollTop = this.myScrollContainer.nativeElement.scrollHeight;
    } catch(err) { }
  }

  formatDate(date: any): string {
    if (!date) return '';
    const d = new Date(date);
    return d.toLocaleTimeString([], { hour: '2-digit', minute: '2-digit' });
  }

  getOtherUserId(conv: ConversationDTO): number {
    const currentId = this.authService.getUserId() || 0;
    return conv.idUser1 === currentId ? conv.idUser2 : conv.idUser1;
  }
}
