//등록 버튼 클릭시 비동기 요청

//버튼 클릭 시 이벤트 생성
//댓글 등록 시 객체 생성
document.getElementById('cmtAddBtn').addEventListener('click',()=>{
    const cmtText = document.getElementById('cmtText').value;
    console.log(cmtText);
    if(cmtText==null || cmtText=="") {
        //댓 없
        alert('댓글을 입력해 주세요');
        return false;
    } else {
        //댓 있 => 저장 객체 생성
        //bnoVal : list.jsp에서 bno 받아옴
        let cmtData = {
            bno: bnoVal,
            writer: document.getElementById('cmtWriter').value,
            content: cmtText
        };
        //댓글 작성 후 보여주기
        postCommentToServer(cmtData).then(result => {
			console.log("resp >>> "+result);
            if(result>0){
                alert("댓글 등록 성공")
            } else {
                alert("댓글 등록 실패")
            }
            printCommentList(cmtData.bno);
        });
    }
})

//입력한 댓글 데이터를 받음
async function postCommentToServer(cmtData){
    try {
        const url = "/cmt/post";
        const config = {
            method: 'post',
            headers:{
                'Content-Type':'application/json; charset=utf-8'
            },
            //본문의(body) cmtData을 json 문자열로 변환
            body: JSON.stringify(cmtData)
        };
        //fetch : url, config로 요청 보냄
        const resp = await fetch(url, config);
        //resp.text() : 응답을 텍스트로 받음
        //0 또는 1
        const result = await resp.text();
        return result;
     } catch (error) {
        console.log(error);
    }
}

//[{}, {}, {}] 형태로 데이터가 들어옴
//js 객체 배열 형태
function spreadCommentList(result) {
    console.log(result);
    let div = document.getElementById('accordionExample');
    div.innerHTML = "";
    //댓글 리스트 존재 시, 댓글 출력(리스트의 길이만큼)
    for(let i =0; i<result.length; i++){
        let str = `<div class="accordion-item">`;
        str += `<h2 class="accordion-header" id="heading${i}">`;
        str += `<button class="accordion-button" type="button"`;
        str += `data-bs-toggle="collapse" data-bs-target="#collapse${i}"`;
        str += `aria-expanded="true" aria-controls="collapse${i}">`;
        str += `${result[i].cno}, ${result[i].writer}, ${result[i].regdate}`;
        str += `</button> </h2>`;
        str += `<div id="collapse${i}" class="accordion-collapse collapse show"`;
        str += `data-bs-parent="#accordionExample">`;
        str += `<div class="accordion-body">`;
        // 콘텐츠를 받을 인풋 값
        str += `<input type="text" class="form-control" id="cmtText" value="${result[i].content}">`;
        str += `<button type="button" data-cno="${result[i].cno}" data-writer="${result[i].writer}" class="btn btn-outline-warning cmtModBtn">%</button>`;
        str += `<button type="button" data-cno="${result[i].cno}" class="btn btn-outline-danger cmtDelBtn">X</button>`;
        str += `</div> </div> </div>`;
        // 누적해서 담기
        div.innerHTML += str;
    }   
}

//수정, 삭제 버튼
//전체 화면에 이벤트 리스너 등록
document.addEventListener('click',(e)=>{
	console.log(e.target);
    //수정 버튼 클릭 시
	if(e.target.classList.contains('cmtModBtn')) {
        //e.target.dataset.cno : data-cno
        //수정 버튼의 data-cno 값 저장
		let cno = e.target.dataset.cno;
		console.log(cno);
		
		//수정 구현(수정할 데이터를 객체로 생성->컨트롤러에서 수정 요청)
		//타겟을 기준으로 가장 가까운 div 찾기
        //수정 버튼 기준 가장 가까운 상위 dib 요소 저장
		let div = e.target.closest('div');
        //변수 div에서 id가 cmtText인 요소 저장
		let cmtText = div.querySelector('#cmtText').value;
        //수정 버튼의 data-writer 값 저장
        let writer = e.target.dataset.writer;
		console.log(cmtText);
        //비동기 통신 함수 호출 -> 처리
        updateCommentFromServer(cno, writer, cmtText).then(result=>{
            console.log(cmtText);
				if(result>0) {
                alert('댓글 수정 성공');
                //수정된 댓글 뿌리기
                printCommentList(bnoVal);
            } else {
                alert('댓글 수정 실패');
            }
        })
	}
    if(e.target.classList.contains('cmtDelBtn')) {
        let cno = e.target.dataset.cno

        removeCommentFromServer(cno).then(result=>{
            if(result>0) {
                alert('댓글 삭제 성공');
                printCommentList(bnoVal);
            } else {
                alert('댓글 삭제 실패');
            }
        })
    }
})

//서버에 댓글 리스트 요청
async function getCommentListFromServer(bno) {
    try{
        //cmt/list로 이동
        // /cmt/list/151, 151 : 글 번호
        //fetch : url, config로 요청 보냄
        const resp = await fetch('/cmt/list/'+bno); 
        //resp.json() : 응답 데이터 파싱
        //result : json 형식 문자열인 댓글 리스트
        //json 형식 데이터를 js로 파싱 
        const result = await resp.json();
        //result : js 객체
        return result;
        }catch(error){
            console.log(error);
        }
    }

//기본 댓글 뿌리기 & 작성 후 뿌리기
//bno : 화면에서 받은 bnoVal
function printCommentList(bno) {
    //cmt/list로 이동
    getCommentListFromServer(bno).then(result=>{
        console.log(result);
        //댓글 리스트가 존재할 경우
        if(result.length>0) {
            //뿌리는 함수 호출
            spreadCommentList(result);
        } else {
            let div = document.getElementById('accordionExample');
            div.innerHTML = `comment가 없습니다`;
        }
    })
}

//입력한 댓글 수정 데이터를 받음
async function updateCommentFromServer(cnoVal, cmtWriter, cmtText){
    try {
        const url = '/cmt/modify';
        const config = {
            method: 'post',
            headers: {
                'Content-Type': 'application/json; charset=utf-8'
            },
            //JSON.stringify() : js 객체를 json 문자열로 변환
            //키-쌍 형태
            body:JSON.stringify({cno:cnoVal, writer:cmtWriter, content:cmtText})
        }

        //fetch : url, config로 요청 보냄
        const resp = await fetch(url, config);
        //resp.text() : 응답을 텍스트로 받음
        //응답 : out.print(isOk)
        const result = await resp.text();
        return result;
    } catch (error) {
        console.log(error);
    }
}

//댓글 삭제
async function removeCommentFromServer(cno) {
    try {
        const url = '/cmt/remove/'+cno;
        const resp = await fetch(url, {method: 'post'});
        const result = await resp.text();
        return result;
    } catch (error) {
        console.log(error);
    }
}