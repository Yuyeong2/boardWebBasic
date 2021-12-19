var frmElem = document.querySelector('#frm');
var submitBtnElem = document.querySelector('#submitBtn');

submitBtnElem.addEventListener('click', function () {
   if(frmElem.upw.value.length == 0) {
      alert("현재 비밀번호를 확인해 주세요");
      return;
   } else if(frmElem.changedUpw.value.length < 5) {
      alert("변경 비밀번호를 확인해주세요");
      return;
   } else if(frmElem.changedupw.value !== frmElem.changedUpwConfirm.value) {
      alert("비밀번호 확인이 틀렸습니다..");
      return;
   }
   frmElem.submit();
});