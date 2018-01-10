/**
 * Created by black on 14-11-21.
 */
/**
 * Created by black on 14-11-21.
 */
describe("guess",function(){
    var compare;
    var answer;
    beforeEach(function () {
        compare = new CompareNumber();
        answer = new AnswerGenerator();
        spyOn(compare,'compareNumber').and.returnValue('4A0B');
        spyOn(answer,'generate_random_number').and.returnValue('1234');
    });
    it("should be able to return 0A0B", function () {
        var gusee = new Guess(answer,compare);
        expect(gusee.game('1234')).toEqual('4A0B');
        expect(answer.generate_random_number).toHaveBeenCalledWith();
    });
    }
);
