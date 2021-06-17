$.ajax({

                  url: 'http://localhost:8080/index.html',

                  data:{
                       'action':'go_ajax',
                       'fn':'spw_autosuggest',
                       'queryString': $.trim(inputString.val())
                       },

                  dataType: 'JSON',

                  success:function(data){
                            // this part is what happens with the JSON data

                            //console.log(data);

                            var content = '';
                            var data = $.parseJSON(data);

                            $.each(data, function(i, post) {
                                content += '<li>' + post.movieid + '</li>';
                            });

                            $(content).appendTo("#hello-movieid");
                    },

                    error: function(errorThrown){
                       alert('error');
                       console.log(errorThrown);
                    }

        });