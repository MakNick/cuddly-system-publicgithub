import {animate, AnimationTriggerMetadata, state, style, transition, trigger} from "@angular/animations";

export const fadeOut: AnimationTriggerMetadata = trigger('fadeOut', [
  state('in', style({opacity: '*'})),
  transition('* => void', [
    style({opacity: '*'}),
    animate(100, style({opacity: 0}))
  ])
]);
