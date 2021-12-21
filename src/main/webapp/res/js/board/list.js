function moveToDetail(iboard) {
    console.log('iboard : ' + iboard);
    location.href="/board/detail2?iboard=" + iboard;
}
var searchfrmElem = document.querySelector('#searchFrm');
if(searchfrmElem) {

    searchfrmElem.rowCnt.addEventListener('change', function(e) {
        searchfrmElem.submit();
    });
}