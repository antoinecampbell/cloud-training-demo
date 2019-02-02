import {Component, OnInit} from "@angular/core";
import {NoteService} from "./note.service";
import {Note} from "./note";
import {MatDialog, MatSnackBar} from "@angular/material";
import {NoteDialogComponent} from "./note-dialog.component";

@Component({
  templateUrl: './note.component.html',
  styleUrls: ['./note.component.less']
})
export class NoteComponent implements OnInit {

  notes: Note[];

  constructor(private noteService: NoteService,
              private matSnackBar: MatSnackBar,
              private matDialog: MatDialog) {
  }

  ngOnInit(): void {
    this.loadNotes();
  }

  loadNotes(): void {
    this.noteService.getNotes()
      .subscribe(response => {
        if (response && response._embedded) {
          this.notes = response._embedded.notes;
        } else {
          this.notes = [];
        }
      }, error => {
        this.matSnackBar.open('Error loading notes', null,
          {duration: 4000, verticalPosition: "bottom"});
        console.error(error);
      });
  }

  onCreateNote(): void {
    const dialogRef = this.matDialog.open(NoteDialogComponent);

    dialogRef.afterClosed().subscribe((savedNote: Note) => {
      if (savedNote) {
        this.loadNotes();
      }
    });
  }

  onEditNote(note: Note): void {
    const dialogRef = this.matDialog.open(NoteDialogComponent, {
      data: note
    });
    dialogRef.afterClosed().subscribe((updatedNote: Note) => {
      if (updatedNote) {
        this.loadNotes();
      }
    });
  }

  onDeleteNote(note: Note): void {
    this.noteService.deleteNote(note)
      .subscribe(() => {
        this.loadNotes();
      }, error => {
        this.matSnackBar.open('Error deleting note', null,
          {duration: 4000, verticalPosition: "bottom"});
        console.error(error);
      });
  }
}
