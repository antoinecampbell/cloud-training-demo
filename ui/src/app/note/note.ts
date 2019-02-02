export class Note {
  id: number;
  title: string;
  description: string;
  owner: string;
  _links: { [name: string]: string };
}

export class NoteEmbedded {
  notes: Note[];
}

export class NotesResponse {
  _embedded: NoteEmbedded;
  _links: { [name: string]: string };
}

