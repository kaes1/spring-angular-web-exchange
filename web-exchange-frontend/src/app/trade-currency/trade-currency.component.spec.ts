import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TradeCurrencyComponent } from './trade-currency.component';

describe('TradeCurrencyComponent', () => {
  let component: TradeCurrencyComponent;
  let fixture: ComponentFixture<TradeCurrencyComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ TradeCurrencyComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(TradeCurrencyComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
