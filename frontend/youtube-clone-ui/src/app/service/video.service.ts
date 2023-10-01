import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {UploadVideoReponse} from "../upload-video/UploadVideoReponse";

@Injectable({
  providedIn: 'root'
})
export class VideoService {

  constructor(private httpClient: HttpClient) {}

  uploadVideo(fileEntry: File) : Observable<UploadVideoReponse> {
    const formData = new FormData()
    formData.append('file', fileEntry, fileEntry.name)

    return this.httpClient.post<UploadVideoReponse>("http://localhost:8080/api/videos", formData);
  }
}
