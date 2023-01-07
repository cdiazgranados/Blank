import React, { useState, useEffect } from 'react';
import { Link, useLocation, useNavigate } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IDirectMessage } from 'app/shared/model/direct-message.model';
import { getEntities } from './direct-message.reducer';

export const DirectMessage = () => {
  const dispatch = useAppDispatch();

  const location = useLocation();
  const navigate = useNavigate();

  const directMessageList = useAppSelector(state => state.directMessage.entities);
  const loading = useAppSelector(state => state.directMessage.loading);

  useEffect(() => {
    dispatch(getEntities({}));
  }, []);

  const handleSyncList = () => {
    dispatch(getEntities({}));
  };

  return (
    <div>
      <h2 id="direct-message-heading" data-cy="DirectMessageHeading">
        <Translate contentKey="jiveApp.directMessage.home.title">Direct Messages</Translate>
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="jiveApp.directMessage.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to="/direct-message/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="jiveApp.directMessage.home.createLabel">Create new Direct Message</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {directMessageList && directMessageList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>
                  <Translate contentKey="jiveApp.directMessage.id">ID</Translate>
                </th>
                <th>
                  <Translate contentKey="jiveApp.directMessage.fromUserId">From User Id</Translate>
                </th>
                <th>
                  <Translate contentKey="jiveApp.directMessage.toUserId">To User Id</Translate>
                </th>
                <th>
                  <Translate contentKey="jiveApp.directMessage.user">User</Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {directMessageList.map((directMessage, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/direct-message/${directMessage.id}`} color="link" size="sm">
                      {directMessage.id}
                    </Button>
                  </td>
                  <td>{directMessage.fromUserId}</td>
                  <td>{directMessage.toUserId}</td>
                  <td>
                    {directMessage.users
                      ? directMessage.users.map((val, j) => (
                          <span key={j}>
                            {val.login}
                            {j === directMessage.users.length - 1 ? '' : ', '}
                          </span>
                        ))
                      : null}
                  </td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`/direct-message/${directMessage.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`/direct-message/${directMessage.id}/edit`}
                        color="primary"
                        size="sm"
                        data-cy="entityEditButton"
                      >
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`/direct-message/${directMessage.id}/delete`}
                        color="danger"
                        size="sm"
                        data-cy="entityDeleteButton"
                      >
                        <FontAwesomeIcon icon="trash" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.delete">Delete</Translate>
                        </span>
                      </Button>
                    </div>
                  </td>
                </tr>
              ))}
            </tbody>
          </Table>
        ) : (
          !loading && (
            <div className="alert alert-warning">
              <Translate contentKey="jiveApp.directMessage.home.notFound">No Direct Messages found</Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

export default DirectMessage;
