import React from 'react';

const style = {
  fontSize: 16
};

export default class Info extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      coordinate: props.coordinate
    }
  }

  componentWillReceiveProps(props) {
    this.setState({coordinate: props.coordinate})
  }

  render() {
    return (
      <a style={style}>Coordinate(x:{this.state.coordinate.x}, y:{this.state.coordinate.y})</a>
    )
  }
}