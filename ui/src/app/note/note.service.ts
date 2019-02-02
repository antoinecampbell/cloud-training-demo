import {Injectable} from "@angular/core";
import {Observable, throwError} from "rxjs";
import {HttpClient} from "@angular/common/http";
import {Note, NotesResponse} from "./note";

export interface INoteService {
  getNotes(): Observable<NotesResponse>;

  createNote(note: Note): Observable<Note>;

  updateNote(note: Note): Observable<Note>;

  deleteNote(note: Note): Observable<Note>;
}

@Injectable()
export class NoteService implements INoteService {

  constructor(private httpClient: HttpClient) {
  }

  getNotes(): Observable<NotesResponse> {
    return this.httpClient.get('/note-service/notes') as Observable<NotesResponse>;
  }

  createNote(note: Note): Observable<Note> {
    return this.httpClient.post('/note-service/notes', note) as Observable<Note>;
  }

  updateNote(note: Note): Observable<Note> {
    if (note && note._links && note._links['self']) {
      return this.httpClient.put(note._links['self']['href'], note) as Observable<Note>;
    } else {
      return throwError(new Error('Unable to update note with missing link'));
    }
  }

  deleteNote(note: Note): Observable<Note> {
    if (note && note._links && note._links['self']) {
      return this.httpClient.delete(note._links['self']['href']) as Observable<Note>;
    } else {
      return throwError(new Error('Unable to delete note with missing link'));
    }
  }
}
