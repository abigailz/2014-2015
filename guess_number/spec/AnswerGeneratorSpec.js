/**
 * Created by black on 14-11-21.
 */
describe("random_number generate", function () {
   var number;
   beforeEach(function () {
      number = new AnswerGenerator();
   });
   it("should be able to four no repeat number", function () {
       expect(judge_number(number.generate_random_number())).toEqual(true);
   });
});
function judge_number(input){
    if(is_number(input)){
        return (input.split("").length==4 & _.uniq(input.split("")).length==4)? true:false;
    }else{
        return false;
    }
}
function is_number(input){
    return !isNaN(input);
}
