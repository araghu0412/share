import { HttpClient } from '@angular/common/http';
import { Component, OnInit, ViewChild } from '@angular/core';
import { Router } from '@angular/router';
import services from 'src/assets/services/services.json';

@Component({
  selector: 'app-bhaaratha-shares-bulk-upload',
  templateUrl: './bhaaratha-shares-bulk-upload.component.html',
  styleUrls: ['./bhaaratha-shares-bulk-upload.component.css']
})
export class BhaarathaSharesBulkUploadComponent implements OnInit {

  @ViewChild("bhaarathaSharesBulkUploadFileUpload") bhaarathaSharesBulkUploadFileUpload: any;

  loading: boolean = false;
  file: any = null;
  filename: string = ""
  showSuccess: boolean = false;
  successMessage: string = "";
  showError: boolean = false;
  errorMessage: string = "";

  constructor(
    private http: HttpClient,
    private router: Router
  ) { }

  ngOnInit(): void {
  }

  onHomeButtonClick() {
    this.router.navigate(["/users/BHA/EQ/home"]);
  }

  onBoughtButtonClick() {
    this.router.navigate(["/users/BHA/EQ/bought"]);
  }

  onSoldButtonClick() {
    this.router.navigate(["/users/BHA/EQ/sold"]);
  }

  onDownloadBulkUploadDocumentClick() {
    this.http.get(services.bhaarathaSharesDownloadBulkUploadDocument, { observe: "response",  responseType: 'blob' }).subscribe((response: any) => {

      let blob: Blob = response.body as Blob;
      let a = document.createElement("a");
      a.download = response.headers.get("fileName");
      a.href = window.URL.createObjectURL(blob);
      a.click();
    }, error => {
      error.error.error.map((e: any) => {
        this.errorMessage = e.errorMessage;
        this.showError = true;
      });
    });
  }

  uploadFileClick() {
    (document.getElementById("file-upload") as HTMLElement).click();
  }

  onInputFileUploadChange(event: any) {
    this.file = event.target.files[0];
    this.filename = this.file.name;
  }

  onBulkUploadClear() {
    this.file = null;
    this.filename = "";
    this.bhaarathaSharesBulkUploadFileUpload.nativeElement.value = "";
  }

  onBulkUploadSubmit() {
    this.loading = true;

    let formData = new FormData();
    formData.append("file", this.file);

    this.http.post(services.bhaarathaSharesBulkUploadDocument, formData).subscribe((response: any) => {
      this.successMessage = response.message;
      this.showSuccess = true;
      this.file = null;
      this.filename = "";
      this.loading = false;
    }, error => {
      error.error.error.map((e: any) => {
        this.errorMessage = e.errorMessage;
        this.showError = true;
      });
      this.loading = false;
    });
  }

  closeError() {
    this.errorMessage = "";
    this.showError = false;
  }

  closeSuccess() {
    this.successMessage = "";
    this.showSuccess = false;
  }
}
