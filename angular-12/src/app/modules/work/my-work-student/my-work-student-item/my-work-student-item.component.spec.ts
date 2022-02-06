import { ComponentFixture, TestBed } from '@angular/core/testing';

import { MyWorkStudentItemComponent } from './my-work-student-item.component';

describe('MyWorkStudentItemComponent', () => {
  let component: MyWorkStudentItemComponent;
  let fixture: ComponentFixture<MyWorkStudentItemComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ MyWorkStudentItemComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(MyWorkStudentItemComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
