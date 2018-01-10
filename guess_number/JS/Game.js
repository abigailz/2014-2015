/**
 * Created by black on 14-11-22.
 */
var answer_generate = new AnswerGenerator();
var answer = answer_generate.generate_random_number();
//console.info(answer);
$(document).ready(function(){
    $("h2").append(append_form());
    $("button").on('click',get_answer);
});
function get_answer(){
    var number = $("#input").val();
    if (check(number)){
        alert("输入不为空且必须为4位不重复的数字并且不能重复输入相同数字!");
    }else{
        var num = $(this).closest("div").find(".times").text() - 1;
        if (num == 0) {
            game_over();
        }
        $(this).closest("div").find(".times").text(num);
        $(this).closest("div").find(".answer").append("第" + (6 - num) + "次输入是：" + number + " 结果是： " + display(number,answer) + "<br/>");
    }
}
function check(number) {
    var flag = false;
    var result = sessionStorage.getItem("number" || '');
    result = result ? result.split(",") : [];
    if(number == ""){
        flag = true;
    }else{
        if (result.indexOf(number) < 0 && judge_number(number)) {
            result.push(number);
        }else {
            flag = true;
        }
        sessionStorage.setItem("number", result);
    }
    return flag;
}
function append_form() {
    return "<div><h5>Please input your number( <span class='times'>6</span> times): </h5>" +
           "<input id='input' type='text'/><br/>" +
           "<button class='btn btn-group-lg btn-primary'>确认</button><br/>" +
           "<span class='answer'></span></div>";
}
function display(number,answer){
    var result = compare(number,answer);
    if(result == "4A0B"){
        sessionStorage.clear();
        $(".game").html("<h1>Congratulations!</h1><br/><p>the answer is   " + number + "</p><a href='game.html'><h4>重新开始Game</h4></a>");
    }
    return result;
}
function compare(input,answer) {
    var compare_number = new CompareNumber();
    return compare_number.compareNumber(input,answer);
}
function game_over(){
    sessionStorage.clear();
    $(".game").html("<h1>Game Over</h1><br/><a href='game.html'><h4>重新开始</h4></a>");
}
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