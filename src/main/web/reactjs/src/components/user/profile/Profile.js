import React from 'react';
import './Profile.css';
import defaultPic from '../../../img/default-avatar.png';
import {Image} from "react-bootstrap";

const Profile = currentUser => {
    return (
        <div>
            <div className="profile-container">
                <div className="container">
                    <div className="profile-info">
                        <div className="profile-avatar">
                            <Image src={currentUser.imageUrl || defaultPic}/>
                        </div>
                        <div className="profile-name">
                            <h2>{currentUser.username}</h2>
                            <p className="profile-email">{currentUser.email}</p>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    );
}

export default Profile;