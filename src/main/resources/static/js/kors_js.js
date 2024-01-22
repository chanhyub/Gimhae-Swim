var sub_menu_data = '.md_menu_bottom p';
var sub_menu_data2 = '.md_sub_list';

$(function(){
    $('.kors_top_menu').mouseover(function(){
        $('.pc_sub_menu_bg').addClass('show');
        $('.top_sub_list').addClass('show');
    })

    $('.kors_top_menu').mouseout(function(){
        $('.pc_sub_menu_bg').removeClass('show');
        $('.top_sub_list').removeClass('show');
    })

    slide_list_set(sub_menu_data, sub_menu_data2)

    $('#main_banner1_show').slick({
        slide: 'div',		//슬라이드 되어야 할 태그 ex) div, li 
        infinite : true, 	//무한 반복 옵션	 
        slidesToShow : 6,		// 한 화면에 보여질 컨텐츠 개수        
        speed : 100,	 // 다음 버튼 누르고 다음 화면 뜨는데까지 걸리는 시간(ms)
        arrows : true, 		// 옆으로 이동하는 화살표 표시 여부
        dots : false, 		// 스크롤바 아래 점으로 페이지네이션 여부
        autoplay : true,			// 자동 스크롤 사용 여부
        autoplaySpeed : 3000, 		// 자동 스크롤 시 다음으로 넘어가는데 걸리는 시간 (ms)
        pauseOnHover : true,		// 슬라이드 이동	시 마우스 호버하면 슬라이더 멈추게 설정
        draggable : true, 	//드래그 가능 여부 
        
        responsive: [ // 반응형 웹 구현 옵션
            {  
                breakpoint: 1610, //화면 사이즈 960px
                settings: {
                    //위에 옵션이 디폴트 , 여기에 추가하면 그걸로 변경
                    slidesToShow:5
                } 
            },
            {  
                breakpoint: 1480, //화면 사이즈 960px
                settings: {
                    //위에 옵션이 디폴트 , 여기에 추가하면 그걸로 변경
                    slidesToShow:4
                } 
            },
            {  
                breakpoint: 1200, //화면 사이즈 960px
                settings: {
                    //위에 옵션이 디폴트 , 여기에 추가하면 그걸로 변경
                    slidesToShow:3 
                } 
            },
            { 
                breakpoint: 576, //화면 사이즈 768px
                settings: {	
                    //위에 옵션이 디폴트 , 여기에 추가하면 그걸로 변경
                    slidesToShow:1
                } 
            }
        ]

    });
    setTimeout(function(){
        $('#main_banner1_show').addClass('show');
    }, 200)

    $('#main_slide2_box').slick({
        slide: 'div',		//슬라이드 되어야 할 태그 ex) div, li 
        infinite : true, 	//무한 반복 옵션	 
        slidesToShow : 4,		// 한 화면에 보여질 컨텐츠 개수        
        speed : 100,	 // 다음 버튼 누르고 다음 화면 뜨는데까지 걸리는 시간(ms)
        arrows : true, 		// 옆으로 이동하는 화살표 표시 여부
        dots : false, 		// 스크롤바 아래 점으로 페이지네이션 여부
        autoplay : true,			// 자동 스크롤 사용 여부
        autoplaySpeed : 3000, 		// 자동 스크롤 시 다음으로 넘어가는데 걸리는 시간 (ms)
        pauseOnHover : true,		// 슬라이드 이동	시 마우스 호버하면 슬라이더 멈추게 설정
        draggable : true, 	//드래그 가능 여부 
        
        responsive: [ // 반응형 웹 구현 옵션
            {  
                breakpoint: 960, //화면 사이즈 960px
                settings: {
                    //위에 옵션이 디폴트 , 여기에 추가하면 그걸로 변경
                    slidesToShow:3 
                } 
            },
            { 
                breakpoint: 768, //화면 사이즈 768px
                settings: {	
                    //위에 옵션이 디폴트 , 여기에 추가하면 그걸로 변경
                    slidesToShow:2 
                } 
            }
        ]
    });

    $('#main_slide3_box').slick({
        slide: 'div',		//슬라이드 되어야 할 태그 ex) div, li 
        infinite : true, 	//무한 반복 옵션	 
        slidesToShow : 7,		// 한 화면에 보여질 컨텐츠 개수        
        speed : 100,	 // 다음 버튼 누르고 다음 화면 뜨는데까지 걸리는 시간(ms)
        arrows : false, 		// 옆으로 이동하는 화살표 표시 여부
        dots : false, 		// 스크롤바 아래 점으로 페이지네이션 여부
        autoplay : true,			// 자동 스크롤 사용 여부
        autoplaySpeed : 3000, 		// 자동 스크롤 시 다음으로 넘어가는데 걸리는 시간 (ms)
        pauseOnHover : true,		// 슬라이드 이동	시 마우스 호버하면 슬라이더 멈추게 설정
        draggable : true, 	//드래그 가능 여부 
        
        responsive: [ // 반응형 웹 구현 옵션
            {  
                breakpoint: 1480, //화면 사이즈 960px
                settings: {
                    //위에 옵션이 디폴트 , 여기에 추가하면 그걸로 변경
                    slidesToShow:5
                } 
            },
            {  
                breakpoint: 960, //화면 사이즈 960px
                settings: {
                    //위에 옵션이 디폴트 , 여기에 추가하면 그걸로 변경
                    slidesToShow:3 
                } 
            },
            { 
                breakpoint: 768, //화면 사이즈 768px
                settings: {	
                    //위에 옵션이 디폴트 , 여기에 추가하면 그걸로 변경
                    slidesToShow:2 
                } 
            }
        ]
    });
})

function me_menu_on(){
    $('.md_menu_box').show();
    $('body, html').css('overflow','hidden');
    setTimeout(function(){
        $('.md_menu_box').addClass('show');
    }, 100)
}

function me_menu_off(){
    $('.md_menu_box').removeClass('show');
    $('body, html').css('overflow','');
    setTimeout(function(){
        $('.md_menu_box').hide();
    }, 350)
}


function slide_list_set(btn_data, list_data){
    $(btn_data).click(function(){
        if($(this).hasClass('active')){
            $(this).removeClass('active');
            $(list_data).slideUp();
        }else{
            $(btn_data).removeClass('active');
            $(list_data).slideUp();
            $(this).addClass('active');
            $(this).next().slideDown();
        }
    })
}


function exportExcel(){ 
  // step 1. workbook 생성
  var wb = XLSX.utils.book_new();

  // step 2. 시트 만들기 
  var newWorksheet = excelHandler.getWorksheet();

  // step 3. workbook에 새로만든 워크시트에 이름을 주고 붙인다.  
  XLSX.utils.book_append_sheet(wb, newWorksheet, excelHandler.getSheetName());

  // step 4. 엑셀 파일 만들기 
  var wbout = XLSX.write(wb, {bookType:'xlsx',  type: 'binary'});

  // step 5. 엑셀 파일 내보내기 
  saveAs(new Blob([s2ab(wbout)],{type:"application/octet-stream"}), excelHandler.getExcelFileName());
}

var excelHandler = {
    getExcelFileName : function(){
        return 'table-test.xlsx';	//파일명
    },
    getSheetName : function(){
        return 'Table Test Sheet';	//시트명
    },
    getExcelData : function(){
        return document.getElementById('print_table'); 	//TABLE id
    },
    getWorksheet : function(){
        return XLSX.utils.table_to_sheet(this.getExcelData());
    }
}

function s2ab(s) { 
  var buf = new ArrayBuffer(s.length); //convert s to arrayBuffer
  var view = new Uint8Array(buf);  //create uint8array as viewer
  for (var i=0; i<s.length; i++) view[i] = s.charCodeAt(i) & 0xFF; //convert to octet
  return buf;    
}