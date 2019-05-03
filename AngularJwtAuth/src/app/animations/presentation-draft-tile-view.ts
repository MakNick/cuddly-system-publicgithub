import {animate, AnimationTriggerMetadata, state, style, transition, trigger} from "@angular/animations";

export const fadeOut: AnimationTriggerMetadata = trigger('fadeOut', [
  state('in', style({
    opacity: '*'
  })),
  transition('* => *', [
    style({
      opacity: '*'
    }),
    animate(100, style({
      opacity: 1
    }))
  ])
]);
