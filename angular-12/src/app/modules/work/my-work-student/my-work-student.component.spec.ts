import { ComponentFixture, TestBed } from '@angular/core/testing';

import { MyWorkStudentComponent } from './my-work-student.component';

describe('MyWorkStudentComponent', () => {
  let component: MyWorkStudentComponent;
  let fixture: ComponentFixture<MyWorkStudentComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ MyWorkStudentComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(MyWorkStudentComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
