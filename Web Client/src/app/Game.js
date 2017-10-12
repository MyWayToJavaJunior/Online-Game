import React from 'react';

import Board from './Board';

const style = {
  container: {
    width: 730,
    display: "block",
    marginTop: 50,
    marginLeft: "auto",
    marginRight: "auto",
  }
};

const label = "X";
const length = 21;

export default class Game extends React.Component {
  constructor() {
    super();

    let temp = [];
    Array(length).fill(null).forEach(() => {
      temp.push(Array(length).fill(null))
    });

    const socket = new WebSocket("ws://localhost:7070/");

    socket.onopen = () => {
      console.log("Connection established");
      socket.send(JSON.stringify({x: 0, y: 0}));
    };

    socket.onclose = function(event) {
      console.log("Connection closed");
    };

    const self = this;
    socket.onmessage = function(event) {
      const data = JSON.parse(event.data);

      self.setBoard(data);
    };

    this.state = {
      squares: temp,
      socket: socket,
      me: {x: 0, y: 0}
    };
  }

  setBoard(data) {
    data.forEach((i) => {
      if (i.me) this.state.me = i;
    });

    const bias = 10;
    const x = this.state.me.x;
    const y = this.state.me.y;

    let squares = [];
    Array(length).fill(null).forEach(() => {
      squares.push(Array(length).fill(null))
    });

    data.forEach((i) => {
      squares[bias + i.y - y][bias + i.x - x] = label;
    });

    this.setState({squares: squares})
  }

  handleKeyPress = (event) => {
    switch (event.key) {
      case "w":
        console.log(event.key);
        this.state.socket.send(JSON.stringify({x:this.state.me.x, y: this.state.me.y-1}));
        break;
      case "a":
        console.log(event.key);
        this.state.socket.send(JSON.stringify({x:this.state.me.x-1, y: this.state.me.y}));
        break;
      case "s":
        console.log(event.key);
        this.state.socket.send(JSON.stringify({x:this.state.me.x, y: this.state.me.y+1}));
        break;
      case "d":
        console.log(event.key);
        this.state.socket.send(JSON.stringify({x:this.state.me.x+1, y: this.state.me.y}));
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