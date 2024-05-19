

function handleReactionClick(icon) {
    var type = $(icon).data('type');
    var videoId = $(icon).data('video');
    var username = $(icon).data('username');
    var reactionCount = $(icon).data('reactioncount');

    // Parse reactionCount to an integer
    reactionCount = parseInt(reactionCount);

    // Send an AJAX request to the server to update the video reactions
    $.ajax({
        type: 'POST',
        url: '/api/video/reactions',
        data: JSON.stringify({ videoId: videoId, reaction: type, username: username }),
        contentType: 'application/json',
        success: function(response) {
            console.log('AJAX response:', response);

            if (response === "Reaction added") {
                reactionCount += 1;
                $(icon).addClass('liked');
            } else if (response === "Reaction removed") {
                reactionCount -= 1;
                $(icon).removeClass('liked');
            }

            // Update the icon's reactionCount data attribute
            $(icon).data('reactioncount', reactionCount);

            // Update the text content of the span next to the icon
            $(icon).siblings('span').text(reactionCount);
        },
        error: function(xhr, status, error) {
            console.error("Error handling reaction: " + error);
        }
    });
}





function handleFriendRequest(button)  {

    var friend = $(button).data('friend');
    var username = $(button).data('username');

    $.ajax({
        type: 'POST',
        url: '/api/user/friendSend',
        data: JSON.stringify({ friend: friend, username : username   }),
        contentType: 'application/json',
        success: function(response) {
            // Update the video reactions count on the UI
            if (response === "request sent") {



            }

            else if (response === "request cancelled") {



            }
        },
        error: function(xhr, status, error) {
            console.error("Error handling friendRequest: " + error);
        }
    });

}


