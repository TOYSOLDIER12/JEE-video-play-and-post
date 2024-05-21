function deleteUser(element) {
    const username = element.getAttribute('data-username');
    if (confirm('Are you sure you want to delete this user?')) {
        $.ajax({
            url: '/api/admin/delete-user/',
            type: 'POST',
            contentType: 'application/json',
            data: JSON.stringify({ username: username }),
            success: function(result) {
                location.reload();
            },
            error: function(xhr, status, error) {
                alert('Failed to delete user: ' + error);
            }
        });
    }
}

function deleteVideo(element) {
    const videoId = element.getAttribute('data-video-id');
    if (confirm('Are you sure you want to delete this video?')) {
        $.ajax({
            url: '/api/admin/delete-video/',
            type: 'POST',
            contentType: 'application/json',
            data: JSON.stringify({ videoId: videoId }),
            success: function(result) {
                location.reload();
            },
            error: function(xhr, status, error) {
                alert('Failed to delete video: ' + error);
            }
        });
    }


}