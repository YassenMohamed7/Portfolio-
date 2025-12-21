

const gitHubProfile = "https://github.com/YassenMohamed7";
const linkedInProfile = "https://www.linkedin.com/in/yassin-mohamed-a900891b3/";

function getGitHubProfile() {
    return gitHubProfile;
}

function getLinkedInProfile() {
    return linkedInProfile;
}


document.getElementById("gitHub").href = getGitHubProfile();
document.getElementById("linkedin").href = getLinkedInProfile();