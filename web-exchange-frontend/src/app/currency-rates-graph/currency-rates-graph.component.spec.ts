import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CurrencyRatesGraphComponent } from './currency-rates-graph.component';

describe('CurrencyRatesGraphComponent', () => {
  let component: CurrencyRatesGraphComponent;
  let fixture: ComponentFixture<CurrencyRatesGraphComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ CurrencyRatesGraphComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(CurrencyRatesGraphComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
