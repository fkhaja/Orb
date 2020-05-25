import React, {Component} from 'react';
import './Profile.css';
import defaultPic from '../../../img/default-avatar.png';
import {Image} from "react-bootstrap";

export default class Profile extends Component {

    render() {
        return (
            <div>
                <div className="profile-container">
                    <div className="container">
                        <div className="profile-info">
                            <div className="profile-avatar">
                                <Image src={this.props.currentUser.imageUrl || defaultPic}/>
                            </div>
                            <div className="profile-name">
                                <h2>{this.props.currentUser.username}</h2>
                                <p className="profile-email">{this.props.currentUser.email}</p>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        );
    }
}