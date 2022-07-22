$(function(){

    
    const orderId = $('#orderId');

    $('#orderId').on('blur', function(){
        $('#errMsg').text('');
  
    })

    $('#orderId').keypress(function(e){

        if(e.which == 13){
            $('#selectOne').click();
        }
    })
    
    // selectOne
    $('#selectOne').on('click', function(){
//        console.log(orderId.val());
        let errMsg;
        let numCheck = /^[0-9]*$/;
        if(orderId.val().trim() == null || orderId.val().trim() == ''){
            
            errMsg = '*請輸入訂單編號'
            $('#errMsg').text(errMsg);

            return;
        }

        if(!numCheck.test(orderId.val().trim())){

            errMsg = '*請輸入數字'
            $('#errMsg').text(errMsg);

            return;
        }

        $.ajax({
            url: 'AdminOrderServlet',
            type: 'POST',
            data: JSON.stringify({orderId : orderId.val().trim()}),
            dataType: 'json',
            success: function(data){
                 console.log(data);
                // console.log(data.orderId);
                $('#errMsg').text('');
                if(data.errMsg == '無此訂單'){
                    errMsg = '*無此訂單'
                    $('#errMsg').text(errMsg);
                    return;
                }

                let table_html = `
                <tr>
                    <td>${data.Orders.orderId}</td>
                    <td>${data.Orders.userId}</td>
                    <td>${data.Orders.resId}</td>
                    <td>${data.Orders.delId}</td>
                    <td>${data.Orders.orderStatus}</td>
                    <td>${data.Orders.note == undefined ? '' : data.Orders.note}</td>
                    <td>${data.Orders.userLocation}</td>
                    <td>${data.Orders.orderCreate}</td>
                    <td>${data.Orders.orderDone}</td>
                    <td>${data.Orders.productKcalTotal}</td>
                    <td>${data.Orders.total}</td>
                    <td>${data.Orders.delCost}</td>
                    <td>${data.Orders.useCash}</td>
                    <td>${data.Orders.creditId == undefined ? '' : data.Orders.creditId}</td>
                    <td>${data.Orders.bonus == undefined ? '' : data.Orders.bonus}</td>
                    <td>${data.Orders.rating == undefined ? '' : data.Orders.rating}</td>
                    <td>${data.Orders.resRate == undefined ? '' : data.Orders.resRate}</td>
                    <td>${data.Orders.delRate == undefined ? '' : data.Orders.delRate}</td>
                    <td>${data.Orders.resComment == undefined ? '' : data.Orders.resComment}</td>
                    <td>${data.Orders.delComment == undefined ? '' : data.Orders.delComment}</td>
                    <td>${data.Orders.promoteId == undefined ? '' : data.Orders.promoteId}</td>
                </tr>
                `

                $('#content').html(table_html);
            }


        })
    })

    // selectAll

    $('#selectAll').on('click', function(){
        let errMsg;

        $.ajax({
            url: 'AdminOrderAllServlet',
            type: 'POST',
            dataType: 'json',
            // dataType: 'json',
            success: function(data){
                 console.log(data);
                 console.log(data.Orders[1]);
                $('#errMsg').text('');

                if(data.errMsg == '系統錯誤'){
                    errMsg = '*系統錯誤'
                    $('#errMsg').text(errMsg);
                    return;
                }

                let table_html = `
                <tr>
                    <td>${data.Orders.orderId}</td>
                    <td>${data.Orders.userId}</td>
                    <td>${data.Orders.resId}</td>
                    <td>${data.Orders.delId}</td>
                    <td>${data.Orders.orderStatus}</td>
                    <td>${data.Orders.note == undefined ? '' : data.Orders.note}</td>
                    <td>${data.Orders.userLocation}</td>
                    <td>${data.Orders.orderCreate}</td>
                    <td>${data.Orders.orderDone}</td>
                    <td>${data.Orders.productKcalTotal}</td>
                    <td>${data.Orders.total}</td>
                    <td>${data.Orders.delCost}</td>
                    <td>${data.Orders.useCash}</td>
                    <td>${data.Orders.creditId == undefined ? '' : data.Orders.creditId}</td>
                    <td>${data.Orders.bonus == undefined ? '' : data.Orders.bonus}</td>
                    <td>${data.Orders.rating == undefined ? '' : data.Orders.rating}</td>
                    <td>${data.Orders.resRate == undefined ? '' : data.Orders.resRate}</td>
                    <td>${data.Orders.delRate == undefined ? '' : data.Orders.delRate}</td>
                    <td>${data.Orders.resComment == undefined ? '' : data.Orders.resComment}</td>
                    <td>${data.Orders.delComment == undefined ? '' : data.Orders.delComment}</td>
                    <td>${data.Orders.promoteId == undefined ? '' : data.Orders.promoteId}</td>
                </tr>
                `

                $('#content').html(table_html);
            }


        })
    })
    
})
