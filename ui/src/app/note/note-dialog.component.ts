import {Component, Inject} from "@angular/core";
import {MAT_DIALOG_DATA, MatDialogRef, MatSnackBar} from "@angular/material";
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {NoteService} from "./note.service";
import {Note} from "./note";

@Component({
  templateUrl: './note-dialog.component.html',
  styleUrls: ['./note-dialog.component.less']
})
export class NoteDialogComponent {

  form: FormGroup;

  constructor(private noteService: NoteService,
              private matSnackBar: MatSnackBar,
              private matDialogRef: MatDialogRef<NoteDialogComponent>,
              private fb: FormBuilder,
              @Inject(MAT_DIALOG_DATA) public editNote: Note) {
    const title = editNote && editNote.title || '';
    const description = editNote && editNote.description || '';
    this.form = this.fb.group({
      title: [title, Validators.required],
      description: description
    });
  }

  onSaveNote(): void {
    let note: Note = this.form.value;
    let noteObservable;
    if (this.editNote) {
      note = Object.assign({}, this.editNote, note);
      noteObservable = this.noteService.updateNote(note);
    } else {
      noteObservable = this.noteService.createNote(note);
    }

    noteObservable.subscribe(savedNote => {
      this.matDialogRef.close(savedNote);
    }, error => {
      this.matSnackBar.open('Error saving note', null,
        {duration: 4000, verticalPosition: "bottom"});
      console.error(error);
    });
  }

}
