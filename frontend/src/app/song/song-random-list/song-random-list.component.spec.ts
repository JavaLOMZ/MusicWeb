import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { SongRandomListComponent } from './song-random-list.component';

describe('SongRandomListComponent', () => {
  let component: SongRandomListComponent;
  let fixture: ComponentFixture<SongRandomListComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ SongRandomListComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(SongRandomListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
