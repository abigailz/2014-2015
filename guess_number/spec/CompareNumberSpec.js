/**
 * Created by black on 14-11-21.
 */
describe('compaernumber return *A*B',function(){
    var compare;
    beforeEach(function () {
       compare = new CompareNumber();
    });
    it("should be able to return 0A0B", function () {
        expect(compare.compareNumber('1234','6789')).toEqual('0A0B');
    });
    it("should be able to return 0A1B", function () {
        expect(compare.compareNumber('1234','6781')).toEqual('0A1B');
    });
    it("should be able to return 0A2B", function () {
        expect(compare.compareNumber('1234','6712')).toEqual('0A2B');
    });
    it("should be able to return 0A3B", function () {
        expect(compare.compareNumber('1234','6123')).toEqual('0A3B');
    });
    it("should be able to return 0A4B", function () {
        expect(compare.compareNumber('1234','4321')).toEqual('0A4B');
    });
    it("should be able to return 1A1B", function () {
        expect(compare.compareNumber('1234','1563')).toEqual('1A1B');
    });
    it("should be able to return 1A2B", function () {
        expect(compare.compareNumber('1734','1483')).toEqual('1A2B');
    });
    it("should be able to return 1A3B", function () {
        expect(compare.compareNumber('1234','1423')).toEqual('1A3B');
    });
    it("should be able to return 2A0B", function () {
        expect(compare.compareNumber('1234','1278')).toEqual('2A0B');
    });
    it("should be able to return 2A1B", function () {
        expect(compare.compareNumber('1234','1245')).toEqual('2A1B');
    });
    it("should be able to return 2A2B", function () {
        expect(compare.compareNumber('1234','1243')).toEqual('2A2B');
    });
    it("should be able to return 3A0B", function () {
        expect(compare.compareNumber('1234','1235')).toEqual('3A0B');
    });
    it("should be able to return 4A0B", function () {
        expect(compare.compareNumber('1234','1234')).toEqual('4A0B');
    });
    }
);
