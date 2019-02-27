import {animate, AnimationTriggerMetadata, state, style, transition, trigger} from "@angular/animations";

export const fadeIn: AnimationTriggerMetadata = trigger('fadeIn', [
  transition(':enter', [
    style({opacity: 0}),
    animate('.5s', style({opacity: 1})),
  ]),
  transition(':leave', [
    animate('.5s', style({opacity: 0}))
  ])
]);

export const navLink: AnimationTriggerMetadata = trigger("navLink", [

  state('open', style({
    left: "5px",
    borderLeft: "2px solid #FFE600"
  })),

  state('closed', style({
    left: "0px",
    borderLeft: "0px solid #FFE600"
  })),

  transition('open => closed', [
    animate('.5s')
  ]),

  transition('closed => open', [
    animate('.2s')
  ])
]);
