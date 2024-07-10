import { Max15CharsPipe } from './max15-chars.pipe';

describe('Max15CharsPipe', () => {
  it('create an instance', () => {
    const pipe = new Max15CharsPipe();
    expect(pipe).toBeTruthy();
  });
});
