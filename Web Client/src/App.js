import React from 'react';
import './App.css';

import Game from './app/Game';
import Info from "./app/Info";

class App extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      token: prompt("Input token:"),
      coordinate: {x:0, y:0}
    }
  }

  changeCoordinate = (coord) => {
    this.setState({coordinate: coord})
  };

  render() {
    return (
      <div>
        <Game token={this.state.token} changeCoordinate={this.changeCoordinate} />
        <Info coordinate={this.state.coordinate} />
      </div>
    );
  }
}

export default App;
