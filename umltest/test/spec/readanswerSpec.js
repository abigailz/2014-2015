describe("test the read file answer data", function () {
    it("form the answer.json read answer data", function () {
        var answer = readAnswersData();
        var str = {
            "first": {
                "1": "统一建模语言",
                "2": "封装性",
                "3": "继承性",
                "4": "多态性"
            },
            "second": {
                "1": "B",
                "2": "A"
            },
            "third": {
                "1": "ABD",
                "2": "ABC"
            },
            "fourth": {
                "1": "X",
                "2": "V"
            },
            "fifth": {
                "1": "模型是对现实世界的简化和抽象，模型是对所研究的系统、过程、事物或概念的一种表达形式。可以是物理实体；可以是某种图形；或者是一种数学表达式。"
            }
        };
        expect(answer).toEqual(str);
        console.log("7&&&&&&&&&&&&&&&");
    });

});