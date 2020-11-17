import axios from 'axios'
import * as restConfig from '../../config/restConfig'
import * as actionTypes from './actionTypes'

export const fetchPlaylists = () => {
  const request = axios.get(`${restConfig.ROOT_URL}/playlists`)
  return { type: actionTypes.FETCH_PLAYLISTS, payload: request }
}

export const addPlaylist = playlist => {
  const url = `${restConfig.ROOT_URL}/playlists`
  const request = axios.post(url, playlist)
  return { type: actionTypes.ADD_PLAYLIST, payload: request }
}

export const deletePlaylist = playlistId => {
  const request = axios.delete(`${restConfig.ROOT_URL}/playlists/${playlistId}`)
  return { type: actionTypes.DELETE_PLAYLIST, payload: request }
}

export const playPlaylist = playlistId => {
  const playlistAction = { action: 'PLAY_PLAYLIST', playlistId }
  const request = axios.post(`${restConfig.ROOT_URL}/player`, playlistAction)
  return { type: actionTypes.PLAY_PLAYLIST, payload: request }
}

export const stopPlaylist = () => {
  const playlistAction = { action: 'STOP' }
  const request = axios.post(`${restConfig.ROOT_URL}/player`, playlistAction)
  return { type: actionTypes.STOP_PLAYLIST, payload: request }
}

export const showAddPlaylistModal = () => ({ type: actionTypes.SHOW_ADD_MODAL })

export const hideAddPlaylistModal = () => ({ type: actionTypes.HIDE_ADD_MODAL })

export const showDeletePlaylistModal = playlistToDelete => ({
  type: actionTypes.SHOW_DELETE_MODAL,
  payload: { playlistToDelete },
})

export const hideDeleteModal = () => ({ type: actionTypes.HIDE_DELETE_MODAL })
