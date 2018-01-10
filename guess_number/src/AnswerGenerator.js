/**
 * Created by black on 14-11-21.
 */
function AnswerGenerator() {
}
AnswerGenerator.prototype.generate_random_number = function () {
    var result = '',length = 4;
    for(var i=0;i<length;i++){
        var number = parseInt(Math.random()*10);
        if(result.indexOf(number)<0){
            result += number;
        }else{
            length += 1;
        }
    }
    return result;
};