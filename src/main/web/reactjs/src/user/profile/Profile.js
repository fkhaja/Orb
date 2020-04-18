import React, {Component} from 'react';
import './Profile.css';

export default class Profile extends Component {
    constructor(props) {
        super(props);
        console.log(props);
    }
    render() {
        return (
            <div className="profile-container">
                <div className="container">
                    <div className="profile-info">
                        <div className="profile-avatar">
                            {
                                this.props.currentUser.imageUrl ? (
                                    <img src={this.props.currentUser.imageUrl} alt={this.props.currentUser.username}/>
                                ) : (
                                    <div className="text-avatar">
                                        <span>
                                            {this.props.currentUser.username && this.props.currentUser.username[0].toUpperCase()}
                                        </span>
                                    </div>
                                )
                            }
                        </div>
                        <div className="profile-name">
                            <h2>{this.props.currentUser.username}</h2>
                            <p className="profile-email">{this.props.currentUser.email}</p>
                        </div>
                    </div>
                </div>
            </div>
        );
    }
}