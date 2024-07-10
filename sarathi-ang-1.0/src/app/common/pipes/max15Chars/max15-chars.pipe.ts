import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
  name: 'max15Chars'
})
export class Max15CharsPipe implements PipeTransform {

  transform(value: string): string {
    if (value.length > 15) {
      return value.substring(0, 15) + "...";
    }
    return value;
  }

}
