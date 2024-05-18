

function handleReactionClick(button) {
    var reaction = $(button).data('reaction');
    var videoId = $(button).data('video-id');
    var creatorId = $(button).data('creator-id');

    // Send an AJAX request to the server to update the video reactions
    $.ajax({
        type: 'POST',
        url: '/api/video/reactions',
        data: JSON.stringify({ videoId: videoId, reaction: reaction , creator : creatorId   }),
        contentType: 'application/json',
        success: function(data) {
            // Update the video reactions count on the UI
            var reactionCount = $('#video-reactions-count-' + videoId).text();
            $('#video-reactions-count-' + videoId).text(parseInt(reactionCount) + 1);
        }
    });
}