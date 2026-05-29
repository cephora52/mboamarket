import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})
export class ProductService {

  // Using /api as currently set, adjust if CORS errors appear
  private apiUrl = 'http://localhost:8080/produits';
  private categoriesUrl = 'http://localhost:8080/categories';

  constructor(private http: HttpClient) {}

  getAllProducts(): Observable<any[]> {
    return this.http.get<any>(this.apiUrl).pipe(
      map(data => Array.isArray(data) ? data : (data && data.content ? data.content : []))
    );
  }

  getProductsByAgriculteur(id: number): Observable<any> {
    return this.http.get(`${this.apiUrl}/agriculteur/${id}`);
  }

  getProductById(id: number): Observable<any> {
    return this.http.get(`${this.apiUrl}/${id}`);
  }

  createProduct(product: any): Observable<any> {
    return this.http.post(this.apiUrl, product);
  }

  updateProduct(id: number, product: any): Observable<any> {
    return this.http.put(`${this.apiUrl}/${id}`, product);
  }

  deleteProduct(id: number): Observable<any> {
    return this.http.delete(`${this.apiUrl}/${id}`);
  }

  getCategories(): Observable<any> {
    return this.http.get(this.categoriesUrl);
  }
}
