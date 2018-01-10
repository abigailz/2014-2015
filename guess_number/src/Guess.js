/**
 * Created by black on 14-11-21.
 */
function Guess(answer,compare) {
    this.answer = answer.generate_random_number();
    this.compare = compare.compareNumber;
};
Guess.prototype.game = function(input){
    var result = this.compare(input,this.answer);
    return result;
};