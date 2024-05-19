

function handleReactionClick(button) {
    var type = $(button).data('type');
    var videoId = $(button).data('video');
    var username = $(button).data('username');

    // Send an AJAX request to the server to update the video reactions
    $.ajax({
        type: 'POST',
        url: '/api/video/reactions',
        data: JSON.stringify({ videoId: videoId, reaction: type , username : username   }),
        contentType: 'application/json',
        success: function(response) {
            // Update the video reactions count on the UI
            if (response === "Reaction added") {

                var reactionCount = parseInt($reactionCount.text());
                $reactionCount.text(reactionCount + 1);

                $button.text('Remove Reaction');

            }

            else if (response === "Reaction removed") {

                var reactionCount = parseInt($reactionCount.text());
                $reactionCount.text(reactionCount - 1);
                // Change button text to indicate add option
                $button.text('Add Reaction');

            }
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


