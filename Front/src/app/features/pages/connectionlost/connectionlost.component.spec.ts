import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ConnectionlostComponent } from './connectionlost.component';

describe('ConnectionlostComponent', () => {
  let component: ConnectionlostComponent;
  let fixture: ComponentFixture<ConnectionlostComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ConnectionlostComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(ConnectionlostComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
