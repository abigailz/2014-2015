describe("test the judgement", function () {
    describe("test input the input id and name", function () {
        it("when input id ane name is not null return true", function () {
            expect(judgement_id_name('312011080605227', 'wzz')).toEqual(true);
        });
        it("when input id is not null and name null return false", function () {
            expect(judgement_id_name('312011080605227')).toEqual(false);
            expect(judgement_id_name('', 'wzz')).toEqual(false);
        });
        it("when input the id no standard return false", function () {
            expect(judgement_id_name('123', 'wzz')).toEqual(false);
        });
    });

    describe("test the result toEqual answer or not", function () {
        it("when input alpha result toEqual answer return true", function () {
            expect(judgement_result_answer('A', 'A')).toEqual(true);
        });
        it("when input alpha result not toEqual answer return false", function () {
            expect(judgement_result_answer('A', 'B')).toEqual(false);
        });
        it("when input string result toEqual answer return true", function () {
            expect(judgement_result_answer('统一建模语言', '统一建模语言')).toEqual(true);
        });
        it("when input string result toEqual answer return true", function () {
            expect(judgement_result_answer('统一建模语言', '统一')).toEqual(false);
        });
    });

    describe("test input is or not alpha", function () {
        it("transform Lowercase", function () {
            expect(transform_Uppercase('a')).toEqual('A');
            expect(transform_Uppercase('A')).toEqual('A');
        });
        it("judgement input is or not alpha", function () {
            expect(judgement_input_alpha('A')).toEqual(true);
            expect(judgement_input_alpha('a')).toEqual(true);
            expect(judgement_input_alpha('1')).toEqual(false);
        })
    });
});