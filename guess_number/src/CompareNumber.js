/**
 * Created by black on 14-11-21.
 */
function CompareNumber(){

}
CompareNumber.prototype.compareNumber = function(input,answer){
    var countA = 0;
    var countB = 0;
    _(input.length).times(function (index) {
        if(answer.indexOf(input.charAt(index)) >= 0){
           _.isEqual(input.charAt(index),answer.charAt(index))?countA++:countB++;
        }
    });
    return (countA +'A'+countB +'B');
};