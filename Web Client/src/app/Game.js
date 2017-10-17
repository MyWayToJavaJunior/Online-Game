import React from 'react';

import Board from './Board';
import HeroState from "./base/HeroState";
import Coordinate from "./base/Coordinate";

const style = {
  container: {
    width: 730,
    display: "block",
    marginTop: 50,
    marginLeft: "auto",
    marginRight: "auto",
  }
};

const length = 21;

export default class Game extends React.Component {
  constructor(props) {
    super(props);

    const socket = new WebSocket("ws://localhost:7070/");

    socket.onopen = () => {
      console.log("Connection established");
      console.log(props.token);
      socket.send(JSON.stringify(new HeroState(props.token, new Coordinate(0, 0))));
    };

    socket.onclose = function(event) {
      console.log("Connection closed");
    };

    const self = this;
    socket.onmessage = function(event) {
      const data = JSON.parse(event.data);
      console.log(data);

      self.setBoard(data);
    };

    this.state = {
      squares: this.generateBoard(),
      socket: socket,
      me: {
        coordinate: {x: 0, y: 0}
      }
    };
  }



  generateBoard() {
      let temp = [];
      Array(length).fill(null).forEach(() => {
          temp.push(Array(length).fill(null))
      });

      return temp;
  }

  setBoard(data) {
    this.setState({me: data.me});

    const bias = Math.round(length/2)-1;
    const x = this.state.me.coordinate.x;
    const y = this.state.me.coordinate.y;

    let squares = this.generateBoard();

    data.otherPlayers.forEach((i) => {
      squares[bias + i.coordinate.y - y][bias + i.coordinate.x - x] = i.token;
    });
    squares[bias][bias] = this.state.me.token;

    this.setState({squares: squares});
    this.props.changeCoordinate(this.state.me.coordinate);
  }

  handleKeyPress = (event) => {
    let heroState;
    switch (event.key) {
      case "w":
        heroState = new HeroState(this.props.token, new Coordinate(this.state.me.coordinate.x, this.state.me.coordinate.y-1));
        this.state.socket.send(JSON.stringify(heroState));
        break;
      case "a":
        heroState = new HeroState(this.props.token, new Coordinate(this.state.me.coordinate.x-1, this.state.me.coordinate.y));
        this.state.socket.send(JSON.stringify(heroState));
        break;
      case "s":
        heroState = new HeroState(this.props.token, new Coordinate(this.state.me.coordinate.x, this.state.me.coordinate.y+1));
        this.state.socket.send(JSON.stringify(heroState));
        break;
      case "d":
        heroState = new HeroState(this.props.token, new Coordinate(this.state.me.coordinate.x+1, this.state.me.coordinate.y));
        this.state.socket.send(JSON.stringify(heroState));
        break;
      default:
        break;
    }
  };

  render() {
    return (
      <div style={style.container} onKeyPress={this.handleKeyPress}>
        <Board
          squares={this.state.squares}
        />
      </div>
    );
  }
}
