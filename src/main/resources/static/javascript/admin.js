function deleteVideo(button) {
    const videoId = button.getAttribute('data-video-id');
    if (confirm('Are you sure you want to delete this video?')) {
        $.ajax({
            url: `/admin/delete-video/${videoId}`,
            type: 'DELETE',
            success: function(result) {
                // Handle success
                alert('Video deleted successfully');
                location.reload();
            },
            error: function(error) {
                // Handle error
                alert('Error deleting video');
            }
        });
    }
}

function deleteUser(button) {
    const username = button.getAttribute('data-username');
    if (confirm('Are you sure you want to delete this user?')) {
        $.ajax({
            url: `/admin/delete-user/${username}`,
            type: 'DELETE',
            success: function(result) {
                // Handle success
                alert('User deleted successfully');
                location.reload();
            },
            error: function(error) {
                // Handle error
                alert('Error deleting user');
            }
        });
    }
}
