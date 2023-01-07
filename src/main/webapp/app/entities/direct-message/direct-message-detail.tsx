import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './direct-message.reducer';

export const DirectMessageDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const directMessageEntity = useAppSelector(state => state.directMessage.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="directMessageDetailsHeading">
          <Translate contentKey="jiveApp.directMessage.detail.title">DirectMessage</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{directMessageEntity.id}</dd>
          <dt>
            <span id="fromUserId">
              <Translate contentKey="jiveApp.directMessage.fromUserId">From User Id</Translate>
            </span>
          </dt>
          <dd>{directMessageEntity.fromUserId}</dd>
          <dt>
            <span id="toUserId">
              <Translate contentKey="jiveApp.directMessage.toUserId">To User Id</Translate>
            </span>
          </dt>
          <dd>{directMessageEntity.toUserId}</dd>
          <dt>
            <Translate contentKey="jiveApp.directMessage.user">User</Translate>
          </dt>
          <dd>
            {directMessageEntity.users
              ? directMessageEntity.users.map((val, i) => (
                  <span key={val.id}>
                    <a>{val.login}</a>
                    {directMessageEntity.users && i === directMessageEntity.users.length - 1 ? '' : ', '}
                  </span>
                ))
              : null}
          </dd>
        </dl>
        <Button tag={Link} to="/direct-message" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/direct-message/${directMessageEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default DirectMessageDetail;
