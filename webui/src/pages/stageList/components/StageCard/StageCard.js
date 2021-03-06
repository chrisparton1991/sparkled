import { Card, CardHeader, IconButton, Menu, MenuItem } from '@material-ui/core'
import { MoreVert } from '@material-ui/icons'
import React, { Component } from 'react'
import { connect } from 'react-redux'
import { Link } from 'react-router-dom'
import { showDeleteModal } from '../../actions'

class StageCard extends Component {
  state = { anchorEl: null }

  render() {
    const { stage } = this.props
    const { anchorEl } = this.state

    return (
      <>
        <Card>
          <CardHeader
            action={
              <IconButton onClick={this.openMenu}>
                <MoreVert />
              </IconButton>
            }
            title={stage.name}
          />
        </Card>

        <Menu
          anchorEl={anchorEl}
          open={Boolean(anchorEl)}
          onClose={this.closeMenu}
        >
          <MenuItem component={Link} to={`/stages/${stage.id}`}>
            Edit stage
          </MenuItem>
          <MenuItem onClick={this.showDeleteModal}>Delete stage</MenuItem>
        </Menu>
      </>
    )
  }

  openMenu = event => {
    this.setState({ anchorEl: event.currentTarget })
  }

  closeMenu = () => {
    this.setState({ anchorEl: null })
  }

  showDeleteModal = () => {
    this.closeMenu()

    const { showDeleteModal, stage } = this.props
    showDeleteModal(stage)
  }
}

function mapStateToProps({ page: { stageList } }) {
  return {
    stageToDelete: stageList.stageToDelete,
    deleting: stageList.deleting
  }
}

export default connect(
  mapStateToProps,
  { showDeleteModal }
)(StageCard)
