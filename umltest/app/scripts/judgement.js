function judgement_id_name(id, name) {
    return !!id && !!name && id.length == 15;
}

function judgement_result_answer(result, answer) {
    var res = judgement_input_alpha(result) ? transform_Uppercase(result) : result;
    var ans = judgement_input_alpha(answer) ? transform_Uppercase(answer) : answer;
    return res == ans;
}

function transform_Uppercase(alpha) {
    return alpha.toUpperCase();
}

function judgement_input_alpha(input) {
    return (input >= 'a' && input <= 'z') || (input >= 'A' && input <= 'Z');
}