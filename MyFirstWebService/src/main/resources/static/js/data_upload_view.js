<script type="text/javascript">
    function insert() {
        $(function () {
            var $upload_form = $('#upload_form');
            if (!$('#filename')[0].value) {
                alert("アップロードファイルを指定してください。");
                return;
            }
            var regex = /\\|\//;
            var array = $('#filename')[0].value.split(regex);
            var ajaxUrl = "/upload/" + array[array.length - 1];
            if(window.FormData){
                var formData = new FormData($upload_form[0]);
                $.ajax({
                    type : "POST",                  // HTTP通信の種類
                    url  : ajaxUrl,                 // リクエストを送信する先のURL
                    dataType : "text",              // サーバーから返されるデータの型
                    data : formData,                // サーバーに送信するデータ
                    processData : false,
                    contentType: false,
                }).done(function(data) {        // Ajax通信が成功した時の処理
                    alert("アップロードが完了しました。");
                }).fail(function(XMLHttpRequest, textStatus, errorThrown) { // Ajax通信が失敗した時の処理
                    alert("アップロードが失敗しました。");
                });
            }else{
                alert("アップロードに対応できていないブラウザです。");
            }
        });
    }
</script>