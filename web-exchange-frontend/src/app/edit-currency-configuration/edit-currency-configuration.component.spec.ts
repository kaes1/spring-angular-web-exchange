import { ComponentFixture, TestBed } from '@angular/core/testing';

import { EditCurrencyConfigurationComponent } from './edit-currency-configuration.component';

describe('EditCurrencyConfigurationComponent', () => {
  let component: EditCurrencyConfigurationComponent;
  let fixture: ComponentFixture<EditCurrencyConfigurationComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ EditCurrencyConfigurationComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(EditCurrencyConfigurationComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
