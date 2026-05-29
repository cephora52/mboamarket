import { Injectable } from '@angular/core';
import jsPDF from 'jspdf';
import autoTable from 'jspdf-autotable';

@Injectable({
  providedIn: 'root'
})
export class ReceiptService {

  constructor() { }

  generateReceipt(order: any, type: 'farmer' | 'distributor'): void {
    const doc = new jsPDF();
    const primaryColor = [20, 83, 45]; // #14532d (Footer Dark Green)
    
    // Helper to format prices without weird characters
    const formatPrice = (p: number) => {
      return p.toString().replace(/\B(?=(\d{3})+(?!\d))/g, " ") + " FCFA";
    };

    // --- Header ---
    doc.setFillColor(251, 251, 226); // Light background
    doc.rect(0, 0, 210, 40, 'F');
    
    doc.setFontSize(24);
    doc.setFont('helvetica', 'bold');
    doc.setTextColor(26, 26, 26);
    doc.text('Mboa', 20, 25);
    doc.setTextColor(primaryColor[0], primaryColor[1], primaryColor[2]);
    doc.text('Market', 43, 25);
    
    doc.setFontSize(10);
    doc.setFont('helvetica', 'normal');
    doc.setTextColor(100, 100, 100);
    doc.text('REÇU DE COMMANDE OFFICIEL', 20, 32);
    
    doc.setFontSize(10);
    doc.setTextColor(26, 26, 26);
    doc.text(`Date: ${order.date}`, 150, 20);
    doc.text(`Commande ID: ${order.id || order.rawId}`, 150, 27);

    // --- Participants ---
    doc.setDrawColor(230, 230, 230);
    doc.line(20, 45, 190, 45);

    doc.setFont('helvetica', 'bold');
    doc.setFontSize(12);
    doc.text(type === 'farmer' ? 'Distributeur (Acheteur)' : 'Producteur (Vendeur)', 20, 55);
    
    doc.setFont('helvetica', 'normal');
    doc.setFontSize(11);
    doc.text(type === 'farmer' ? order.distributor : order.producer, 20, 62);

    // --- Items Table ---
    const tableData = order.items.map((item: any) => [
      item.nomProduit,
      item.quantite,
      formatPrice(item.prix),
      formatPrice(item.prix * item.quantite)
    ]);

    autoTable(doc, {
      startY: 75,
      head: [['Produit', 'Quantité', 'Prix Unitaire', 'Total']],
      body: tableData,
      headStyles: {
        fillColor: primaryColor as [number, number, number],
        textColor: [255, 255, 255],
        fontStyle: 'bold'
      },
      alternateRowStyles: {
        fillColor: [248, 250, 248]
      },
      margin: { left: 20, right: 20 },
      styles: {
        fontSize: 9,
        cellPadding: 5
      }
    });

    // --- Footer Summary ---
    const finalY = (doc as any).lastAutoTable.finalY + 10;
    
    doc.setFont('helvetica', 'bold');
    doc.setFontSize(11); // Softer font size
    doc.setTextColor(100, 100, 100);
    doc.text('MONTANT TOTAL:', 130, finalY + 10);
    
    doc.setFontSize(12); // Softer total size
    doc.setTextColor(primaryColor[0], primaryColor[1], primaryColor[2]);
    const totalAmount = order.total || order.amount;
    doc.text(formatPrice(totalAmount), 165, finalY + 10);

    doc.setDrawColor(200, 200, 200);
    doc.setLineWidth(0.3);
    doc.line(130, finalY + 13, 190, finalY + 13);

    // --- Bottom ---
    doc.setFontSize(9);
    doc.setFont('helvetica', 'italic');
    doc.setTextColor(150, 150, 150);
    doc.text('Merci pour votre confiance en Mboa Market - Le cœur de notre agriculture.', 105, 280, { align: 'center' });

    // Download
    const fileName = `Recu_MboaMarket_${order.id || order.rawId}.pdf`;
    doc.save(fileName);
  }
}
