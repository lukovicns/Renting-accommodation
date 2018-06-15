import { trigger, state, style, animate, transition } from '@angular/animations';

export const fadeIn = () => {
  return trigger('fadeIn', [
    transition(':enter', [
      style({opacity: 0}),
      animate('0.4s ease-in-out', style({opacity: 1}))
    ]),
    transition(':leave', [
      style({opacity: 1}),
      animate('0.4s ease-in-out', style({opacity: 0}))
    ])
  ]);
}
