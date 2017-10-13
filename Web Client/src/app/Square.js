import React from 'react';

let style = {
  background: "#fff",
  border: "1px solid #999",
  float: "left",
  fontSize: 24,
  fontWeight: "bold",
  height: 34,
  marginRight: -1,
  marginTop: -1,
  padding: 0,
  textAlign: "center",
  width: 34
};

export default class Square extends React.Component {
  render() {
    return (
      <button style={style}>{this.props.value}</button>
    );
  }
}
